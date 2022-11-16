package project.mini.board.member.mapper;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;

import project.mini.board.member.mapper.MemberHistoryMapper;
import project.mini.board.member.model.Member;

@SpringBootTest
public class MapperTest {
    @Autowired
    private MemberHistoryMapper memberHistoryMapper;

    @Test
    public void test() {
        Member member = Member.builder()
                .id("id")
                .password("password")
                .email("email")
                .nick("nick")
                .build();

        memberHistoryMapper.insertMemberHistory(member);
    }

    @Test
    public void test1() throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] rs = resolver.getResources("classpath*:project/mini/board/**/*Mapper.xml");

        System.out.println(rs.length);

        for(Resource r: rs){
            System.out.println(r.getFilename());
            MetadataReader mr = new SimpleMetadataReaderFactory().getMetadataReader(r);
            System.out.println(mr.getClassMetadata().getClassName());
        }
    }
}
