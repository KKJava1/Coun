package com.studyhot.wiki.service;

import com.studyhot.wiki.domain.Demo;
import com.studyhot.wiki.mapper.DemoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liuhuiping
 * @create 2022-01-14 23:39
 */
@Service
public class DemoService {

    @Resource
    private DemoMapper demoMapper;

    public List<Demo> list(){
        return demoMapper.selectByExample(null);
    }
}
