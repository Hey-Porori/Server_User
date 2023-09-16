package porori.backend.user.domain.user.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import porori.backend.user.global.exception.LocationLimitException;

import javax.persistence.*;
import java.util.List;

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
    private boolean isDefault;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //==연관관계 메서드==//
    public void updateUser(User user) {
        this.user = user;
        List<Location> myLocations = user.getMyLocations();
        if (myLocations.size() < 2) { // 내 위치는 최대 2개
            myLocations.add(this);
        } else {
            throw new LocationLimitException();
        }
    }

    public void updateLocation(Location location){
        this.name=location.getName();
        this.longitude=location.getLongitude();
        this.latitude=location.getLatitude();
    }

    public void selectLocation(){
        this.isDefault=true;
    }
}
