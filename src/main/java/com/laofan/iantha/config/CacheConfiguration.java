package com.laofan.iantha.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(
                Object.class,
                Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries())
            )
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build()
        );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.laofan.iantha.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.laofan.iantha.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.laofan.iantha.domain.User.class.getName());
            createCache(cm, com.laofan.iantha.domain.Authority.class.getName());
            createCache(cm, com.laofan.iantha.domain.User.class.getName() + ".authorities");
            createCache(cm, com.laofan.iantha.domain.Cart.class.getName());
            createCache(cm, com.laofan.iantha.domain.CartItem.class.getName());
            createCache(cm, com.laofan.iantha.domain.CartItem.class.getName() + ".carts");
            createCache(cm, com.laofan.iantha.domain.Brand.class.getName());
            createCache(cm, com.laofan.iantha.domain.Product.class.getName());
            createCache(cm, com.laofan.iantha.domain.Product.class.getName() + ".cartItems");
            createCache(cm, com.laofan.iantha.domain.Product.class.getName() + ".specs");
            createCache(cm, com.laofan.iantha.domain.Product.class.getName() + ".labels");
            createCache(cm, com.laofan.iantha.domain.Product.class.getName() + ".brands");
            createCache(cm, com.laofan.iantha.domain.Product.class.getName() + ".categories");
            createCache(cm, com.laofan.iantha.domain.Category.class.getName());
            createCache(cm, com.laofan.iantha.domain.BabySpec.class.getName());
            createCache(cm, com.laofan.iantha.domain.BabyLabel.class.getName());
            createCache(cm, com.laofan.iantha.domain.Order.class.getName());
            createCache(cm, com.laofan.iantha.domain.Order.class.getName() + ".payments");
            createCache(cm, com.laofan.iantha.domain.Order.class.getName() + ".discountCodes");
            createCache(cm, com.laofan.iantha.domain.OrderItem.class.getName());
            createCache(cm, com.laofan.iantha.domain.Address.class.getName());
            createCache(cm, com.laofan.iantha.domain.Notification.class.getName());
            createCache(cm, com.laofan.iantha.domain.DiscountCode.class.getName());
            createCache(cm, com.laofan.iantha.domain.Refund.class.getName());
            createCache(cm, com.laofan.iantha.domain.Refund.class.getName() + ".orders");
            createCache(cm, com.laofan.iantha.domain.Payment.class.getName());
            createCache(cm, com.laofan.iantha.domain.PaymentProvider.class.getName());
            createCache(cm, com.laofan.iantha.domain.PaymentProvider.class.getName() + ".orders");
            createCache(cm, com.laofan.iantha.domain.Banner.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
