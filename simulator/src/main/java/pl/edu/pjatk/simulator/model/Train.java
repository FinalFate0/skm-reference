package pl.edu.pjatk.simulator.model;

import pl.edu.pjatk.simulator.service.Identifiable;
import pl.edu.pjatk.simulator.util.PersonGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "trains")
public class Train implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Compartment> compartments;

    @Column(name = "current_station", columnDefinition = "enum('DUMMY')")
    @Enumerated(EnumType.STRING)
    private Station currentStation;

    @Column(name = "going_to_gdansk")
    private boolean goingToGdansk;

    @Column(name = "current_pause_time")
    private int currentPauseTime;

    public Train() {

    }

    public Set<Compartment> getCompartments() {
        return compartments;
    }

    public void setCompartments(Set<Compartment> compartments) {
        this.compartments = compartments;
    }

    public Station getCurrentStation() {
        return currentStation;
    }

    public void setCurrentStation(Station currentStation) {
        this.currentStation = currentStation;
    }

    public boolean isGoingToGdansk() {
        return goingToGdansk;
    }

    public void setGoingToGdansk(boolean goingToGdansk) {
        this.goingToGdansk = goingToGdansk;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCurrentPauseTime() {
        return currentPauseTime;
    }

    public void setCurrentPauseTime(int currentPauseTime) {
        this.currentPauseTime = currentPauseTime;
    }

    public void move() {
        if (currentPauseTime > 0) {
            currentPauseTime--;
        } else {
            int nextStationModifier = goingToGdansk ? 1 : -1;
            currentStation = Station.values()[currentStation.ordinal() + nextStationModifier];
            currentPauseTime = currentStation.getPauseTime();

            if (currentStation.getPauseTime() > 0) {
                goingToGdansk = !goingToGdansk;
            }

            compartments.forEach(c -> c.disembark(this.currentStation));
            compartments.forEach(c -> {
                List<Person> people = PersonGenerator.generatePeople(this.currentStation);
                people.forEach(c::embark);
            });
        }
    }
}
