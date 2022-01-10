package spring.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    private static final MemberRepository instance = new MemberRepository(); // 싱글톤

    private MemberRepository() {

    }
    public static MemberRepository getInstance() {
        return instance;
    }

    public Member save(Member member) {     // 저장
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }
    public Member findById(Long id) {       // 아이디로 멤버 찾기
        return store.get(id);
    }
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
    public void clearStore() {
        store.clear();
    }
}
