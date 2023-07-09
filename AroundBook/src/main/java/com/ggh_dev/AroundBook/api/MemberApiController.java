package com.ggh_dev.AroundBook.api;

import com.ggh_dev.AroundBook.domain.member.Member;
import com.ggh_dev.AroundBook.service.MemberService;
import com.ggh_dev.AroundBook.web.argumentresolver.Login;
import com.ggh_dev.AroundBook.web.dto.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberApiController {
    private final MemberService memberService;

    /**
     * 회원 등록 API
     */
    public ResponseEntity saveMember(@RequestBody @Valid MemberDTO.CreateMemberDTO createMemberDTO, BindingResult result){
        Long id=memberService.join(createMemberDTO);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @Data
    static class CreateMemberResponse{
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    /**
     * 회원 정보 수정 API
     */
    @PatchMapping
    public ResponseEntity updateMemberResponse(@Login Member member,
                                                     @RequestBody @Valid UpdateMemberRequest request){
        memberService.update(member.getId(), request.getName());
        Member findMember=memberService.findOne(member.getId());
        return ResponseEntity.status(HttpStatus.OK).body(member.getId());

        //return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    static class UpdateMemberRequest{
        private String name;
        private String password;
    }
    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        private Long id;
        private String name;
    }

    /**
     * 전체 회원 조회
     */
    @GetMapping
    public MemberDTO.Result members(){
        List<Member> members = memberService.findMembers();

        List<Object> collect = members.stream()
                .map(m -> new MemberDTO.MemberListDto(m.getName()))
                .collect(Collectors.toList());
        return new MemberDTO.Result(collect);
    }

}
