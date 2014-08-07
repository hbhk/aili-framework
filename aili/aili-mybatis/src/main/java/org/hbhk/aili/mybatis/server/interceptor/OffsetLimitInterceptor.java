package org.hbhk.aili.mybatis.server.interceptor;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.hbhk.aili.mybatis.server.dialect.Dialect;
import org.hbhk.aili.mybatis.share.util.PropertiesHelper;

/**
 * 为ibatis3提供基于方言(Dialect)的分页查询的插件
 * 将拦截Executor.query()方法实现分页方言的插入.
 */

@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
    RowBounds.class, ResultHandler.class }) })
public class OffsetLimitInterceptor implements Interceptor {
    private static int mappedStatementIndex = 0;
    private static int parameterIndex = 1;
    private static int rowboundsIndex = 2;
  //  private static int resultHandlerIndex = 3;

    // 设置方言
    private Dialect dialect;

    public Object intercept(Invocation invocation) throws Throwable {
        processIntercept(invocation.getArgs());
        return invocation.proceed();
    }

    /**
     * 拦截分页请求，使用方言将原sql转化成分页sql processIntercept
     */
    void processIntercept(final Object[] queryArgs) {
        // queryArgs = query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler)
        MappedStatement ms = (MappedStatement) queryArgs[mappedStatementIndex];
        Object parameter = queryArgs[parameterIndex];
        final RowBounds rowBounds = (RowBounds) queryArgs[rowboundsIndex];
        int offset = rowBounds.getOffset();
        int limit = rowBounds.getLimit();

        if (dialect.supportsLimit() && (offset != RowBounds.NO_ROW_OFFSET || limit != RowBounds.NO_ROW_LIMIT)) {
            BoundSql boundSql = ms.getBoundSql(parameter);
            String sql = boundSql.getSql().trim();
            if (dialect.supportsLimitOffset()) {
                sql = dialect.getLimitString(sql, offset, limit);
                offset = RowBounds.NO_ROW_OFFSET;
            } else {
                sql = dialect.getLimitString(sql, 0, limit);
            }
            limit = RowBounds.NO_ROW_LIMIT;

            queryArgs[rowboundsIndex] = new RowBounds(offset, limit);
            BoundSql newBoundSql =
                new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
            // 将原有的BoundSql中的MetaParameter复制到新的BoundSql中
            copyMetaParameters(boundSql, newBoundSql);
            MappedStatement newMs = copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql));
            queryArgs[mappedStatementIndex] = newMs;
        }
    }
        
    /**
     * <p>复制BoundSql的MetaParameter</p> 
     * @param lhs
     * @param rhs
     */
    private void copyMetaParameters(BoundSql lhs, BoundSql rhs) {
        for (ParameterMapping map : lhs.getParameterMappings()) {
            String key = map.getProperty();
            Object value = lhs.getAdditionalParameter(key);
            if (null != value) {
                rhs.setAdditionalParameter(key, value);
            }
        }
    }

    /**
     * <p>获取MappedStatement</p> 
     */
    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        Builder builder =
            new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());

        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        builder.keyProperty(ms.getKeyProperties()!=null && ms.getKeyProperties().length > 0 ? ms.getKeyProperties()[0] : null);

        // setStatementTimeout()
        builder.timeout(ms.getTimeout());

        // setStatementResultMap()
        builder.parameterMap(ms.getParameterMap());

        // setStatementResultMap()
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());

        // setStatementCache()
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
    /**
     * <p>设置方言</p> 
     */
    public void setProperties(Properties properties) {
        String dialectClass = new PropertiesHelper(properties).getRequiredString("dialectClass");
        try {
            dialect = (Dialect) Class.forName(dialectClass).newInstance();
        } catch (Exception e) {
            throw new RuntimeException("cannot create dialect instance by dialectClass:" + dialectClass, e);
        }
    }
    /**
     * 设置分页boundsql
     */
    public static class BoundSqlSqlSource implements SqlSource {
        private BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }

}
