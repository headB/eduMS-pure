package cn.wolfcode.edums.market.service;

import cn.wolfcode.edums.market.domain.Client;
import cn.wolfcode.edums.teaching.qo.StudentChartsQueryObject;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Leon
 * @since 2020-02-03
 */
public interface IClientService extends IService<Client> {

    List<JSONObject> statIntention(StudentChartsQueryObject qo);

    List<JSONObject> statRecommend(StudentChartsQueryObject qo);

    List<JSONObject> queryRegularStudents(StudentChartsQueryObject qo);
}
