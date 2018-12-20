

package com.github.wxiaoqi.search.service.impl;

import com.github.wxiaoqi.search.lucene.LuceneDao;
import com.github.wxiaoqi.search.service.LuceneService;
import com.github.wxiaoqi.security.api.vo.search.IndexObject;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Description:luncene
 *
 * @author ace
 * @create 2017-06-06
 **/
@Service
public class LuceneServiceImpl implements LuceneService {

    @Autowired
    private LuceneDao luceneDao;


    @Override
    public void save(IndexObject indexObject) {
        luceneDao.create(indexObject);
    }


    @Override
    public void update(IndexObject indexObject) {
        luceneDao.update(indexObject);
    }

    @Override
    public void delete(IndexObject indexObject) {
        luceneDao.delete(indexObject);
    }

    @Override
    public void deleteAll() {
        luceneDao.deleteAll();
    }

    @Override
    public TableResultResponse page(Integer pageNumber, Integer pageSize, String keyword) {
        return luceneDao.page(pageNumber,pageSize,keyword);
    }
}
