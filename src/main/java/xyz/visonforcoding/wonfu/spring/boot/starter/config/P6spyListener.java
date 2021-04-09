package xyz.visonforcoding.wonfu.spring.boot.starter.config;

import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.common.PreparedStatementInformation;
import com.p6spy.engine.common.StatementInformation;

import com.p6spy.engine.event.JdbcEventListener;
import java.sql.SQLException;

/**
 *
 * @author vison.cao <visonforcoding@gmail.com>
 */
//@Configuration
public class P6spyListener extends JdbcEventListener {

    @Override
    public void onAfterGetConnection(ConnectionInformation connectionInformation, SQLException e) {

    }

    @Override
    public void onAfterExecuteQuery(PreparedStatementInformation statementInformation, long timeElapsedNanos, SQLException e) {
        App.sqlCount.set(App.sqlCount.get() + 1);
        Long duration = timeElapsedNanos / 1000000;
        App.sqlDuration.set(App.sqlDuration.get() + duration);
        Log.info(String.format("执行sql || %s 耗时 %s ms", statementInformation.getSqlWithValues(), duration));
    }

    @Override
    public void onAfterExecuteUpdate(PreparedStatementInformation statementInformation, long timeElapsedNanos, int rowCount, SQLException e) {
        App.sqlCount.set(App.sqlCount.get() + 1);
        Log.info(App.sqlCount.get().toString());
        Long duration = timeElapsedNanos / 1000000;
        App.sqlDuration.set(App.sqlDuration.get() + duration);
        String singleLineSql = statementInformation.getSqlWithValues().replaceAll("\n", "\\\\n");
        Log.info(String.format("执行sql || %s 耗时 %s ms", singleLineSql, duration));
    }

    @Override
    public void onAfterExecute(StatementInformation statementInformation, long timeElapsedNanos, String sql, SQLException e) {
        Log.info("execute..", statementInformation.getSqlWithValues());
    }

}
