package org.example.redistemplateexample.redis;

import jakarta.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class SetOperation {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * Adds the specified members to the set stored at the given key.
     *
     * @param key    the key of the set
     * @param values the members to be added to the set
     * @return the number of elements that were added to the set
     */
    public Long SAdd(String key, String... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * Returns the number of elements in the set stored at the specified key.
     *
     * @param key the key of the set
     * @return the number of elements in the set
     */
    public Long SCard(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * Returns the difference between the set stored at key and the set stored at otherKey.
     *
     * @param key the key of the set
     * @param otherKey the key of the other set
     * @return the set difference as a new set
     */
    public Set<String> SDiff(String key, String otherKey) {
        return redisTemplate.opsForSet().difference(key, otherKey);
    }

    /**
     * Calculates the difference between the sets stored at key and otherKey, and stores the result in destKey.
     *
     * @param key      the key of the first set
     * @param otherKey the key of the second set
     * @param destKey  the key of the destination set where the result will be stored
     * @return the number of elements in the resulting set stored at destKey
     */
    public Long SDiffStore(String key, String otherKey, String destKey){
        return redisTemplate.opsForSet().differenceAndStore(key, otherKey, destKey);
    }

    /**
     * Computes the intersection between the sets stored at `key` and `otherKey`.
     * 
     * @param key the key of the first set
     * @param otherKey the key of the second set
     * @return the intersection of the sets as a new set
     */
    public Set<String> SInter(String key, String otherKey) {
        return redisTemplate.opsForSet().intersect(key, otherKey);
    }

    /**
     * Computes the intersection of sets stored at key and otherKey, and stores the result in the destination key destKey.
     * 
     * @param key the key of the first set
     * @param otherKey the key of the second set
     * @param destKey the key of the destination set
     * @return the number of elements in the resulting set
     */
    public Long SInterStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKey, destKey);
    }

    /**
     * Checks if a value is a member of a set stored at the given key.
     *
     * @param key   the key of the set
     * @param value the value to check for membership
     * @return true if the value is a member of the set, false otherwise
     */
    public boolean SIsMember(String key, String value) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, value));
    }

    /**
     * Retrieves all the members of a set stored at the specified key.
     *
     * @param key the key of the set
     * @return a set containing all the members of the set at the specified key
     */
    public Set<String> SMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * Moves a member from the source set to the destination set.
     *
     * @param sourceKey the key of the source set
     * @param value the member to be moved
     * @param destKey the key of the destination set
     * @return true if the member was moved successfully, false otherwise
     */
    public boolean SMove(String sourceKey, String value, String destKey) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().move(sourceKey, value, destKey));
    }

    /**
     * Removes and returns a random member from the set stored at the specified key.
     *
     * @param key the key of the set
     * @return the removed random member, or null if the set is empty
     */
    public String SPop(String key) {
        return redisTemplate.opsForSet().pop(key);
    }

    /**
     * Returns a random member from the set stored at the specified key.
     *
     * @param key the key of the set
     * @return a random member from the set, or null if the set is empty
     */
    public String SRandMember(String key) {
        return redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * Retrieves random members from a set.
     *
     * @param key   the key of the set
     * @param count the number of random members to retrieve
     * @return a list of random members from the set
     */
    public List<String> SRandMember(String key, long count) {
        return redisTemplate.opsForSet().randomMembers(key, count);
    }   

    /**
     * Removes the specified members from the set stored at the given key.
     *
     * @param key    the key of the set
     * @param values the members to be removed from the set
     * @return the number of members removed from the set
     */
    public Long SRem(String key, String... values) {
        return redisTemplate.opsForSet().remove(key, (Object[]) values);
    }   

    /**
     * Returns the union of sets stored at `key` and `otherKey`.
     *
     * @param key the key of the first set
     * @param otherKey the key of the second set
     * @return the union of the two sets
     */
    public Set<String> SUnion(String key, String otherKey) {
        return redisTemplate.opsForSet().union(key, otherKey);
    }

    /**
     * Computes the union of two sets and stores the result in a new set.
     *
     * @param key the key of the first set
     * @param otherKey the key of the second set
     * @param destKey the key of the destination set where the result will be stored
     * @return the number of elements in the resulting set
     */
    public Long SUnionStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
    }
}
