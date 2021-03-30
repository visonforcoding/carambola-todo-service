package xyz.visonforcoding.wonfu.spring.boot.starter.config;

import com.p6spy.engine.event.JdbcEventListener;
import com.p6spy.engine.spy.P6LoadableOptions;
import com.p6spy.engine.spy.P6SpyOptions;
import com.p6spy.engine.spy.option.P6OptionsRepository;

/**
 *
 * @author vison.cao <visonforcoding@gmail.com>
 */
public class CustomeP6Factory implements com.p6spy.engine.spy.P6Factory {

    @Override
    public P6LoadableOptions getOptions(P6OptionsRepository optionsRepository) {
        return new P6SpyOptions(optionsRepository);
    }

    @Override
    public JdbcEventListener getJdbcEventListener() {
        return new P6spyListener();
    }

}
