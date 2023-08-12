package porori.backend.user.domain.user.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import porori.backend.user.domain.user.application.dto.request.UserLocationRequest;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long locationId;

    private String name;
    private Double longitude;
    private Double latitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //==연관관계 메서드==//
    public void updateUser(User user) {
        this.user=user;
        user.getMyLocations().add(this);
    }

    public void updateLocation(Location location){
        this.name=location.getName();
        this.longitude=location.getLongitude();
        this.latitude=location.getLatitude();
    }
}
