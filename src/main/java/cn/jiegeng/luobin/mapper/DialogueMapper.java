package cn.jiegeng.luobin.mapper;

import cn.jiegeng.luobin.domain.dto.Dialogue;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DialogueMapper {
   List<String> getDialogues(@Param("nowTime") int nowTime);
List<Dialogue> getDialogsDto();
int addDialogue( Dialogue dialogue);
}
