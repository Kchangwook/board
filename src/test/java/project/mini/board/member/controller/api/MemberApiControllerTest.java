package project.mini.board.member.controller.api;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import project.mini.board.member.model.Member;
import project.mini.board.member.service.MemberService;

@ExtendWith(MockitoExtension.class)
public class MemberApiControllerTest {
	@Mock
	private MemberService memberService;

	@InjectMocks
	private MemberApiController memberApiController;

	private MockMvc mockMvc;

	private ObjectMapper objectMapper;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(memberApiController)
			.build();

		objectMapper = new ObjectMapper();
	}

	@Test
	@DisplayName("회원가입 API 테스트")
	public void addMemberTest() throws Exception {
		//given
		Member member = Member.builder()
			.id("id")
			.password("password")
			.email("email")
			.nick("nick")
			.build();

		//when
		ResultActions resultActions = mockMvc.perform(post("/api/member")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(objectMapper.writeValueAsString(member)));

		//then
		resultActions.andExpect(status().isOk());

		verify(memberService, times(1)).addMember(any(Member.class));
	}
}
