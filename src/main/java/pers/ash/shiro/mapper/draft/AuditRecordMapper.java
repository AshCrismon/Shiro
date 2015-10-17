package pers.ash.shiro.mapper.draft;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pers.ash.shiro.model.draft.AuditRecord;
import pers.ash.shiro.model.draft.AuditRecordExample;

public interface AuditRecordMapper {
    int countByExample(AuditRecordExample example);

    int deleteByExample(AuditRecordExample example);

    int deleteByPrimaryKey(String id);

    int insert(AuditRecord record);

    int insertSelective(AuditRecord record);

    List<AuditRecord> selectByExample(AuditRecordExample example);

    AuditRecord selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AuditRecord record, @Param("example") AuditRecordExample example);

    int updateByExample(@Param("record") AuditRecord record, @Param("example") AuditRecordExample example);

    int updateByPrimaryKeySelective(AuditRecord record);

    int updateByPrimaryKey(AuditRecord record);
}