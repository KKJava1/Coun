package com.studyhot.wiki.controller;

import com.studyhot.wiki.req.EbookQueryReq;
import com.studyhot.wiki.req.EbookSaveReq;
import com.studyhot.wiki.resp.CommonResp;
import com.studyhot.wiki.resp.EbookQueryResp;
import com.studyhot.wiki.resp.PageResp;
import com.studyhot.wiki.service.EbookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author liuhuiping
 * @create 2022-01-14 18:22
 */
@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp list(@Valid EbookQueryReq req){
        CommonResp<PageResp<EbookQueryResp>> resp = new CommonResp<>();
        PageResp<EbookQueryResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp list(@Valid @RequestBody EbookSaveReq req){
        CommonResp resp = new CommonResp();
        ebookService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        CommonResp resp = new CommonResp();
        ebookService.delete(id);
        return resp;
    }

}
