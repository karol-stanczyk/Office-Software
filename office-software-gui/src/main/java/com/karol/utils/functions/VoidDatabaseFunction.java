package com.karol.utils.functions;

import com.karol.repository.utils.DatabaseException;

@FunctionalInterface
public interface VoidDatabaseFunction {

    void run() throws DatabaseException;
}
