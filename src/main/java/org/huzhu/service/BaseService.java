package org.huzhu.service;

import org.huzhu.result.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wjh on 16/2/2.
 */
public interface BaseService {
    Result testCase(HttpServletRequest request, String caseName) throws Exception;
    Result config(HttpServletRequest request, String caseName) throws Exception;
}
