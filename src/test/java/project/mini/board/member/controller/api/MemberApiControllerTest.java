package project.mini.board.member.controller.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.servlet.http.Cookie;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;
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

	@Test
	@DisplayName("중복 체크 API 테스트")
	public void checkDuplicateMemberTest() throws Exception {
		//given
		String memberId = "id";
		Member member = Member.builder()
			.id("id")
			.password("password")
			.email("email")
			.nick("nick")
			.build();

		when(memberService.getMemberById(anyString())).thenReturn(member);

		//when
		ResultActions resultActions = mockMvc.perform(get("/api/member/duplicate-check?memberId=" + memberId));

		//then
		resultActions.andExpect(status().isOk());

		String resultString = resultActions.andReturn().getResponse()
				.getContentAsString();
		assertEquals(resultString, "true");

		verify(memberService, times(1)).getMemberById(anyString());
	}

	@Test
	@DisplayName("로그인 테스트")
	public void loginTest() throws Exception {
		//given
		String cookieValue = "cookieValue";

		Member member = Member.builder()
			.id("id")
			.password(DigestUtils.sha3_256Hex("password"))
			.email("email")
			.nick("nick")
			.build();

		when(memberService.getMemberById(anyString())).thenReturn(member);

		//when
		ResultActions resultActions = mockMvc.perform(post("/api/member/login")
			.param("id", "id")
			.param("password", "password"));

		//then
		resultActions.andExpect(status().isOk());

		Cookie resultCookie = resultActions.andReturn().getResponse()
				.getCookie("loginMember");
		assertNotNull(resultCookie);

		verify(memberService, times(1)).getMemberById(anyString());
	}

	@Test
	@DisplayName("회원 정보 수정 테스트")
	public void modifyMemberTest() throws Exception {
		//given
		Member member = Member.builder()
			.id("id")
			.password(DigestUtils.sha3_256Hex("password"))
			.email("email")
			.nick("nick")
			.build();

		//when
		ResultActions resultActions = mockMvc.perform(put("/api/member")
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(objectMapper.writeValueAsString(member)));

		//then
		resultActions.andExpect(status().isOk());

		verify(memberService, times(1)).modifyMember(any(Member.class));
	}

	@Test
	@DisplayName("회원 비밀번호 수정 테스트")
	public void modifyMemberPasswordTest() throws Exception {
		//given
		Member member = Member.builder()
			.id("id")
			.password("password")
			.newPassword("newPassword")
			.email("email")
			.nick("nick")
			.build();

		when(memberService.modifyMemberPassword(any(Member.class))).thenReturn(true);

		//when
		ResultActions resultActions = mockMvc.perform(put("/api/member/password")
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(objectMapper.writeValueAsString(member)));

		//then
		resultActions.andExpect(status().isOk());
		MockHttpServletResponse mockResponse = resultActions.andReturn()
				.getResponse();

		assertEquals(mockResponse.getContentAsString(), "true");
		verify(memberService, times(1)).modifyMemberPassword(any(Member.class));
	}
}
