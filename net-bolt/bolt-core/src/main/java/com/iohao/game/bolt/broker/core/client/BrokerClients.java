/*
 * ioGame
 * Copyright (C) 2021 - 2023  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
 * # iohao.com . 渔民小镇
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package com.iohao.game.bolt.broker.core.client;

import com.iohao.game.action.skeleton.core.commumication.BrokerClientContext;
import org.jctools.maps.NonBlockingHashMap;

import java.util.Map;

/**
 * 管理 BrokerClient
 * <pre>
 *     当然开发者也可以自己定义一个类来管理 BrokerClient
 *
 *     什么情况下需要管理 BrokerClient 呢?
 *     多服单进程时，建议这么管理。 框架是对每个逻辑服会创建一个 BrokerClient
 *     比如：
 *          大厅逻辑服有对应的 BrokerClient
 *          抽奖逻辑服也有对应的一个 BrokerClient
 *
 *     OK，当我上面没说（原因是我不想白打字，就不删除上面这段话了，所以当我上面什么都没说）
 *     实际上即使是多服单进程的方式启动，BrokerClient 是共用游戏网关的，所以只要保存任意一个就可以了
 *     建议使用 {@link BrokerClientHelper} ，在逻辑服启动时，框架会让 {@link BrokerClientHelper} 保持一个 BrokerClient 的引用
 *
 * </pre>
 *
 * @author 渔民小镇
 * @date 2022-05-15
 */
public class BrokerClients {
    final Map<String, BrokerClient> brokerClientMap = new NonBlockingHashMap<>();

    public void put(Class<?> clazz, BrokerClient brokerClient) {
        this.put(clazz.getName(), brokerClient);
    }

    public void put(String key, BrokerClient brokerClient) {
        this.brokerClientMap.put(key, brokerClient);
    }

    public BrokerClientContext getBrokerClient(Class<?> clazz) {
        return getBrokerClient(clazz.getName());
    }

    public BrokerClientContext getBrokerClient(String key) {
        return this.brokerClientMap.get(key);
    }

    private BrokerClients() {

    }

    public static BrokerClients me() {
        return Holder.ME;
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final BrokerClients ME = new BrokerClients();
    }

}
