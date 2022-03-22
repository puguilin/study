package com.guilin.studycode.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guilin.studycode.entrity.SysLog;
import com.guilin.studycode.mapper.SysLogMapper;
import com.guilin.studycode.service.SysLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @description:
 * @author: puguilin
 * @date: 2022/3/22
 * @version: 1.0
 */
@Service("sysLogService")
public class SysLogServiceImpl   extends ServiceImpl<SysLogMapper, SysLog>  implements SysLogService {
    @Resource
    private SysLogMapper sysLogMapper;

    @Override
    public int insertLog(SysLog entity) {
        // TODO Auto-generated method stub
        int insert = sysLogMapper.insert(entity);
        System.out.println("insert " + insert);
        return insert;
    }

    @Override
    public boolean save(SysLog entity) {
        return false;
    }

    @Override
    public boolean saveBatch(Collection<SysLog> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<SysLog> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean removeById(Serializable id) {
        return false;
    }

    @Override
    public boolean removeByMap(Map<String, Object> columnMap) {
        return false;
    }

    @Override
    public boolean remove(Wrapper<SysLog> queryWrapper) {
        return false;
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        return false;
    }

    @Override
    public boolean updateById(SysLog entity) {
        return false;
    }

    @Override
    public boolean update(SysLog entity, Wrapper<SysLog> updateWrapper) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<SysLog> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(SysLog entity) {
        return false;
    }

    @Override
    public SysLog getById(Serializable id) {
        return null;
    }

    @Override
    public Collection<SysLog> listByIds(Collection<? extends Serializable> idList) {
        return null;
    }

    @Override
    public Collection<SysLog> listByMap(Map<String, Object> columnMap) {
        return null;
    }

    @Override
    public SysLog getOne(Wrapper<SysLog> queryWrapper, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<SysLog> queryWrapper) {
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<SysLog> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public int count(Wrapper<SysLog> queryWrapper) {
        return 0;
    }

    @Override
    public List<SysLog> list(Wrapper<SysLog> queryWrapper) {
        return null;
    }

    @Override
    public IPage<SysLog> page(IPage<SysLog> page, Wrapper<SysLog> queryWrapper) {
        return null;
    }

    @Override
    public List<Map<String, Object>> listMaps(Wrapper<SysLog> queryWrapper) {
        return null;
    }

    @Override
    public <V> List<V> listObjs(Wrapper<SysLog> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public IPage<Map<String, Object>> pageMaps(IPage<SysLog> page, Wrapper<SysLog> queryWrapper) {
        return null;
    }

}
