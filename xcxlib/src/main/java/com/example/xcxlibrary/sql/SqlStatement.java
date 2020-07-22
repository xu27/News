package com.example.xcxlibrary.sql;

public class SqlStatement {
    public static String TABNAME = "test_tb";
    public static String INSERT_TEST_DB = "insert into test_tb values (?,?,?)";
    public static String SELECT_TEST_DB = "select * from test_tb";
    public static String DELETE_IN_TEST_DB = "Delete from test_tb where name=?";
    public static String UPDATE_TESTDB_BYNAME = "Update test_tb set name=?,age=? where name=?";
}
