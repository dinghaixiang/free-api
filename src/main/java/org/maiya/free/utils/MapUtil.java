package org.maiya.free.utils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Map操作工具类
 *
 * @author JounQin
 */
public class MapUtil<K, V> {

    /**
     * 需要操作的Map
     */
    private Map<K, V> map;

    /**
     * 需要放入Map中的key的名称
     */
    private K key;

    /**
     * 需要比较的字符串
     */
    private K str;

    public MapUtil() {

    }

    public MapUtil(Map<K, V> map) {
        this.map = map;
    }

    public MapUtil(Map<K, V> map, K key, K str) {
        this.map = map;
        this.key = key;
        this.str = str;
    }

    public boolean containsKeys(K... keys) {
        return containsKeys(false, keys);
    }

    /**
     * 判断map中是否包含所有的keys
     *
     * @param delExKeys 是否删除多余的，即删除第一个以外的其他key
     * @param keys      需要判断的keys数组
     * @return 是否包含
     */
    public boolean containsKeys(boolean delExKeys, K... keys) {
        boolean flag = true;
        for (K key : keys) {
            if (!map.containsKey(key))
                flag = false;
        }

        if (flag && delExKeys) {
            this.removeKeys(Arrays.copyOfRange(keys, 1, keys.length));
        }

        return flag;
    }

    /**
     * 移除map中key为keys数组中的元素，并返回删除的键对应值的集合
     *
     * @param keys 需要删除的键数组
     * @return 删除的键对应值的集合
     */
    public List<V> removeKeys(K... keys) {
        List<V> results = new LinkedList<V>();
        for (K key : keys) {
            results.add(map.remove(key));
        }
        return results;
    }

    /**
     * 将strs数组中的所有与str匹配时，在map中插入以key为键，value为值的元素
     *
     * @param value 需要插入的值
     * @param strs  需要比较的字符串数组
     * @return MapUtil对象本身，方便链式调用
     */
    public MapUtil putKey(V value, K... strs) {
        if (Arrays.asList(strs).contains(str)) {
            map.put(key, value);
        }
        return this;
    }

}
