package dev.fkramar.zootycoon.Account.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ZooAdditionRequest {

    private  String zooName;
    private String username;
}
