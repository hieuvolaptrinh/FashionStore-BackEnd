package Model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "type")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int typeId;

    private String typeName;

    @ManyToMany(fetch = FetchType.LAZY, cascade ={
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH
    } )
    @JoinTable(
            name = "product_type",
            joinColumns = @JoinColumn(name = "type_id"),
            inverseJoinColumns = @JoinColumn(name ="product_id" )
    )
    private List<Product> listProducts;
    public Type() {
    }

    public Type(List<Product> listProducts, int typeId, String typeName) {
        this.listProducts = listProducts;
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public List<Product> getListProducts() {
        return listProducts;
    }

    public void setListProducts(List<Product> listProducts) {
        this.listProducts = listProducts;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
