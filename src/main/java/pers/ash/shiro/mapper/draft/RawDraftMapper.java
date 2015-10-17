package pers.ash.shiro.mapper.draft;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pers.ash.shiro.model.draft.RawDraft;
import pers.ash.shiro.model.draft.RawDraftExample;

public interface RawDraftMapper {
    int countByExample(RawDraftExample example);

    int deleteByExample(RawDraftExample example);

    int deleteByPrimaryKey(String id);

    int insert(RawDraft record);

    int insertSelective(RawDraft record);

    List<RawDraft> selectByExampleWithBLOBs(RawDraftExample example);

    List<RawDraft> selectByExample(RawDraftExample example);

    RawDraft selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RawDraft record, @Param("example") RawDraftExample example);

    int updateByExampleWithBLOBs(@Param("record") RawDraft record, @Param("example") RawDraftExample example);

    int updateByExample(@Param("record") RawDraft record, @Param("example") RawDraftExample example);

    int updateByPrimaryKeySelective(RawDraft record);

    int updateByPrimaryKeyWithBLOBs(RawDraft record);

    int updateByPrimaryKey(RawDraft record);
}