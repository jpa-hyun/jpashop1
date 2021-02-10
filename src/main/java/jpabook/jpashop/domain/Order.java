package jpabook.jpashop.domain;
import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery; //배송정보

    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]


    // 주문 함수내에서 사용할 연관 관계 함수는 주문 엔티티 밑에 추가
    // 주문 함수내에서 사용할 연관 관계 함수1
    // 주문 엔티티에 주문 멤버 정보 저장
    // 멤버 엔티티에 주문 정보 저장 
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    // 주문 함수내에서 사용할 연관 관계 함수2
    // 주문 엔티티에 주문한 상품 정보 추가
    // 주문 상품 정보가 어떤 주문에 대한 주문 상품 정보인지에 대해 저장
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    // 주문 함수내에서 사용할 연관 관계 함수3
    // 주문 엔티티에 배송 정보 저장
    // 배송 엔티티에 주문 정보 저장
    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

}
