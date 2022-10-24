package com.studyhot.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.studyhot.wiki.domain.Ebook;
import com.studyhot.wiki.domain.EbookExample;
import com.studyhot.wiki.mapper.EbookMapper;
import com.studyhot.wiki.req.EbookQueryReq;
import com.studyhot.wiki.req.EbookSaveReq;
import com.studyhot.wiki.resp.EbookQueryResp;
import com.studyhot.wiki.resp.PageResp;
import com.studyhot.wiki.util.CopyUtil;
import com.studyhot.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liuhuiping
 * @create 2022-01-14 23:39
 */
@Service
public class EbookService {
    private static final Logger LOG = LoggerFactory.getLogger(EbookService.class);

    @Resource
    private EbookMapper ebookMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<EbookQueryResp> list(EbookQueryReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }
        if (!ObjectUtils.isEmpty(req.getCategoryId2())) {
            criteria.andCategory2IdEqualTo(req.getCategoryId2());
        }
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Ebook> ebooksList = ebookMapper.selectByExample(ebookExample);

        PageInfo<Ebook> pageInfo = new PageInfo<>(ebooksList);
        LOG.info("总条数 ：{}", pageInfo.getTotal());
        LOG.info("总页数 ：{}", pageInfo.getPages());
//        List<EbookQueryResp> respList = new ArrayList<>();
//        for (Ebook ebook : ebooksList) {
//            EbookQueryResp ebookResp = new EbookQueryResp();
//            BeanUtils.copyProperties(ebook, ebookResp);
//            respList.add(ebookResp);
//        }
        //列表复制
        List<EbookQueryResp> respList = CopyUtil.copyList(ebooksList, EbookQueryResp.class);
        PageResp<EbookQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);

        return pageResp;
    }

    public void save(EbookSaveReq req) {
        Ebook ebook = CopyUtil.copy(req, Ebook.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            //新增
            ebook.setId(snowFlake.nextId());//根据雪花算法生成Id
            ebookMapper.insert(ebook);
        }else{
            //更新
            ebookMapper.updateByPrimaryKey(ebook);
        }
    }

    /**
     * 删除电子书
     * @param id
     */
    public void delete(Long id) {
        ebookMapper.deleteByPrimaryKey(id);
    }
}
