package com.whyisee.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CKTest {
    public static void main(String[] args) {
        String v_sql = args[0];
        //String sqlDB = "show databases";//查询数据库
        //String sqlTab = "show tables";//查看表
        //String sqlCount = "select count(*) count from ontime";//查询ontime数据量
        //exeSql(sqlDB);
        //exeSql(sqlTab);
        //exeSql(sqlCount);
        exeSql(v_sql);
    }

    public static void exeSql(String sql) {
        String address = "jdbc:clickhouse://172.16.1.23:8123/default";
        Connection connection = null;
        Statement statement = null;
        ResultSet results = null;

        String url = "cc-2ze79ing76muz352g.ads.aliyuncs.com";
        String username = "data_clickhouse";
        String password = "3C#d3Q5LRb#V1$DTVK";
        String port = "3306";

        //Class.forName("ru.yandex.clickhouse.ClickHouseDriver");
        String connectionStr = "jdbc:clickhouse://" + url + ":"+port;


        try {
            Class.forName("ru.yandex.clickhouse.ClickHouseDriver");
            connection = DriverManager.getConnection(connectionStr, username, password);
            statement = connection.createStatement();
            long begin = System.currentTimeMillis();
            results = statement.executeQuery(sql);
            long end = System.currentTimeMillis();
            System.out.println("执行（" + sql + "）耗时：" + (end - begin) + "ms");
            ResultSetMetaData rsmd = results.getMetaData();
            List<Map> list = new ArrayList();
            while (results.next()) {
                Map map = new HashMap();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    map.put(rsmd.getColumnName(i), results.getString(rsmd.getColumnName(i)));
                }
                list.add(map);
            }
            for (Map map : list) {
                System.err.println(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {//关闭连接
            try {
                if (results != null) {
                    results.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
