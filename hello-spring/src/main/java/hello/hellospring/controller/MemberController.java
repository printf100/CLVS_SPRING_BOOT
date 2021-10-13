package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);

        return "members/memberList";
    }

    @GetMapping("/members/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
        Optional<Member> member = memberService.findOne(id);
        model.addAttribute("member", member.get());

        return "members/editMemberForm";
    }

    @PostMapping("/members/edit/{id}")
    public String editForm(@PathVariable("id") Long id, MemberForm form) {
        Member member = new Member();
        member.setId(id);
        member.setName(form.getName());

        memberService.edit(member);

        return "redirect:/";
    }

    @GetMapping("/members/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        memberService.delete(id);

        return "redirect:/";
    }
}
