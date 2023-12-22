package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.model.PostFavour;
import generator.service.PostFavourService;
import generator.mapper.PostFavourMapper;
import org.springframework.stereotype.Service;

/**
* @author ljl
* @description 针对表【post_favour(帖子收藏)】的数据库操作Service实现
* @createDate 2023-12-21 10:48:06
*/
@Service
public class PostFavourServiceImpl extends ServiceImpl<PostFavourMapper, PostFavour>
    implements PostFavourService{

}




