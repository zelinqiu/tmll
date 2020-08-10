package com.how2java.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.mapper.PropertyValueMapper;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.pojo.PropertyValue;
import com.how2java.tmall.pojo.PropertyValueExample;
import com.how2java.tmall.service.PropertyService;
import com.how2java.tmall.service.PropertyValueService;

/**
 * PropertyValue是Product中设置属性的链接
 * PropertyValue是Property的值本身，例如有种Property为颜色，PropertyValue可以为黑色、白色等
 * 因此它是定位在Product下的值
 * @author qiuzelin
 *
 */
@Service
public class PropertyValueServiceImpl implements PropertyValueService {

	@Autowired
	PropertyValueMapper propertyValueMapper;
	
	@Autowired
	PropertyService propertyService;
	
	/**
	 * 这个方法的作用是初始化PropertyValue
	 * PropertyValue没有增加，只有修改，所以需要通过初始化来进行自动增加，以便于后面的修改
	 */
	public void init(Product p) {
		//根据Product获取cid-->通过PropertyService获取所有的property
		List<Property> pts = propertyService.list(p.getCid());
		
		for (Property pt : pts) {
			//pt.getId()获取Property的id，p.getId()获取Product的id
			PropertyValue pv = get(pt.getId(), p.getId());
			
			if (pv == null) {
				pv = new PropertyValue();
				pv.setPid(p.getId());
				pv.setPtid(pt.getId());
				propertyValueMapper.insert(pv);
			}
		}
	}
	
	
	public void update(PropertyValue pv) {
		propertyValueMapper.updateByPrimaryKeySelective(pv);
	}
	
	/* ptid为属性id  pid为产品id*/
	public PropertyValue get(int ptid, int pid) {
		PropertyValueExample e = new PropertyValueExample();
		e.createCriteria().andPtidEqualTo(ptid).andPidEqualTo(pid);
		List<PropertyValue> pv = propertyValueMapper.selectByExample(e);
		if (pv.isEmpty()) {
			return null;
		}
		return pv.get(0);
	}
	
	
	//根据产品id获取所有属性值
	public List<PropertyValue> list(int pid) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPidEqualTo(pid);
        //由pid获得PropertyValue
        List<PropertyValue> result = propertyValueMapper.selectByExample(example);
        for (PropertyValue pv : result) {
        	//pv.getPtid()获取属性id，ps.get(pv.getPtid())获取属性
            Property property = propertyService.get(pv.getPtid());
            pv.setProperty(property);
        }
        return result;
	}
	
}





















