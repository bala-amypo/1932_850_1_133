@Entity
public class TransferSuggestion {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Store sourceStore;

    @ManyToOne
    private Store targetStore;

    @ManyToOne
    private Product product;

    private Integer quantity;
    private String priority; // HIGH, MEDIUM, LOW
    private String status = "PENDING";
    private LocalDateTime suggestedAt;
}
