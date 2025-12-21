@Entity
public class DemandForecast {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Store store;

    private LocalDate forecastDate;
    private Integer predictedDemand;
    private Double confidenceScore;
}
