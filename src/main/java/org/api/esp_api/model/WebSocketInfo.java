package org.api.esp_api.model;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class WebSocketInfo {
    private String server;
    private int port;
    private String url;

}
