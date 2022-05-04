//package com.unsa.etf.InventoryAndCatalogService.logging;
//
//import com.netflix.appinfo.InstanceInfo;
//import com.netflix.discovery.EurekaClient;
//import io.grpc.ManagedChannelBuilder;
//import lombok.RequiredArgsConstructor;
//import net.devh.boot.grpc.client.inject.GrpcClient;
//import org.springframework.stereotype.Service;
//
//import java.time.Instant;
//
//@Service
//@RequiredArgsConstructor
//public class GrpcSystemEvents {
////    @GrpcClient("systemEvents")
////    ReportSystemEventGrpc.ReportSystemEventBlockingStub stub;
//
//    public void logEvent(String microserviceName, String username, String actionType, String resourceName, String responseType){
////        try{
////            Instant now = Instant.now();
////            InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("SystemEventsService", false);
////            var managedChannel = ManagedChannelBuilder.forAddress(instanceInfo.getIPAddr(), 9090).usePlaintext().build();
////
////        }
//
//    }
//}
