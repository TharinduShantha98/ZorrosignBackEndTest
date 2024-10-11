package com.zorroSign.BackEndTest.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "sub_label_management")
public class SubLabelManagementEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SUB_LABEL_NAME", nullable = false, length = 100)
    private String subLabelName;

    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.DATE)
    private Date createDate;

    @ManyToOne
    @JoinColumn(name = "MAIN_LABEL_ID", nullable = false, foreignKey = @ForeignKey(name = "SUB_LABEL_MANAGEMENT_SUB_LABEL_MANAGEMENT_FK"))
    private MainLabelEntity mainLabel;
}
