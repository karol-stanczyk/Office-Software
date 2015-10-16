package com.karol.utils;

import com.karol.repository.DatabaseException;

@FunctionalInterface
public interface VoidDatabaseFunction {

    void run() throws DatabaseException;
}