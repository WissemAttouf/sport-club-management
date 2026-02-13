package com.sportclub.service;

import com.sportclub.model.Member;
import com.sportclub.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    
    @Autowired
    private MemberRepository memberRepository;
    
    public Member createMember(Member member) {
        if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
            throw new RuntimeException("Email déjà utilisé: " + member.getEmail());
        }
        return memberRepository.save(member);
    }
    
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
    
    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membre non trouvé avec id: " + id));
    }
    
    public Member updateMember(Long id, Member memberDetails) {
        Member member = getMemberById(id);
        
        member.setFirstName(memberDetails.getFirstName());
        member.setLastName(memberDetails.getLastName());
        member.setEmail(memberDetails.getEmail());
        member.setPhone(memberDetails.getPhone());
        member.setAddress(memberDetails.getAddress());
        member.setBirthDate(memberDetails.getBirthDate());
        member.setStatus(memberDetails.getStatus());
        
        return memberRepository.save(member);
    }
    
    public void deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new RuntimeException("Membre non trouvé avec id: " + id);
        }
        memberRepository.deleteById(id);
    }
    
    public List<Member> searchMembers(String name) {
        return memberRepository.findByLastNameContainingIgnoreCase(name);
    }
    
    public long countMembers() {
        return memberRepository.count();
    }
}