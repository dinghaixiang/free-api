package org.maiya.free.utils.eql;

import org.n3r.eql.Eql;
import org.n3r.eql.config.EqlConfig;
import org.n3r.eql.config.EqlDiamondConfig;

/**
 * Created by gucong on 2015/8/7.
 */
public class FreeDql extends Eql {
    public FreeDql() {
        super(createEqlConfig(), Eql.STACKTRACE_DEEP_FIVE);
    }

    public FreeDql(String connectionName) {
        super(createEqlConfig(connectionName), Eql.STACKTRACE_DEEP_FIVE);
    }


    public static EqlConfig createEqlConfig() {
        return createEqlConfig("free");
    }


    public static EqlConfig createEqlConfig(String connectionName) {
        return new EqlDiamondConfig(connectionName);
    }
}
