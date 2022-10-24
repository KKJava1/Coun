package com.studyhot.wiki.service;

import com.studyhot.wiki.domain.Test;
import com.studyhot.wiki.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liuhuiping
 * @create 2022-01-14 23:39
 */
@Service
public class TestService {

    @Resource
    private TestMapper testMapper;

    public List<Test> list(){
        return testMapper.list();
    }
}
