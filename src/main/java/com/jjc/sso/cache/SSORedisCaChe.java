package com.jjc.sso.cache;

import com.baomidou.kisso.SSOCache;
import com.baomidou.kisso.SSOConfig;
import com.baomidou.kisso.Token;
import com.jjc.sso.web.util.WebApplicationContextHelper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;


/**
SSO缓存使用
 缓存TOKEN 退出使用
 */
public class SSORedisCaChe implements SSOCache {

    StringRedisTemplate stringRedisTemplate = (StringRedisTemplate) WebApplicationContextHelper.getBean("stringRedisTemplate");

    @Override
    public Token get(String s, int i) {
        if(exists(s)){
            String result = null;
            ValueOperations operations = stringRedisTemplate.opsForValue();
            result = (String)operations.get(s);
            result = result.replaceAll("\u0000" , "");
            Token token = SSOConfig.getInstance().getParser().parseObject(result, Token.class);
            return  token;
        }
        return null;
    }

    @Override
    public boolean set(String s, Token token, int i) {
        boolean result = false;
        try {
            if (exists(s)) {
                stringRedisTemplate.delete(s);
            }
            ValueOperations valueOperations = stringRedisTemplate.opsForValue();
            valueOperations.set(s,token.jsonToken(),i);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    @Override
    public boolean delete(String s) {
        boolean result = false;
        try{
            if (exists(s)) {
                stringRedisTemplate.delete(s);
                return  true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public boolean exists(final String key) {
        return stringRedisTemplate.hasKey(key);
    }

}
