package pers.ash.shiro.mapper.draft;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pers.ash.shiro.model.draft.Attachment;
import pers.ash.shiro.model.draft.AttachmentExample;

public interface AttachmentMapper {
    int countByExample(AttachmentExample example);

    int deleteByExample(AttachmentExample example);

    int insert(Attachment record);

    int insertSelective(Attachment record);

    List<Attachment> selectByExample(AttachmentExample example);

    int updateByExampleSelective(@Param("record") Attachment record, @Param("example") AttachmentExample example);

    int updateByExample(@Param("record") Attachment record, @Param("example") AttachmentExample example);
}