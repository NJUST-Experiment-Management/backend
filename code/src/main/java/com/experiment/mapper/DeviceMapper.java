package com.experiment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.experiment.entity.Device;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface DeviceMapper extends BaseMapper<Device> {
    @Override
    List<Device> selectBatchIds(Collection<? extends Serializable> idList);
}
