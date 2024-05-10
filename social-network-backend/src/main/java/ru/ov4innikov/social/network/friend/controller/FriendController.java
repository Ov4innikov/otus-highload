package ru.ov4innikov.social.network.friend.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ov4innikov.social.network.api.server.FriendApi;
import ru.ov4innikov.social.network.friend.service.FriendService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Controller
public class FriendController implements FriendApi {

    private final FriendService friendService;

    @Override
    public ResponseEntity<List<String>> friendAllGet(
            @Parameter(name = "offset", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "offset", required = false, defaultValue = "0") Optional<@DecimalMin("0") BigDecimal> offset,
            @Parameter(name = "limit", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "limit", required = false, defaultValue = "10") Optional<@DecimalMin("1") BigDecimal> limit
    ) {
        List<String> friendIds = friendService.getFriendIds();
        return new ResponseEntity<>(friendIds, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> friendDeleteUserIdPut(@Parameter(name = "userId", description = "", required = true, in = ParameterIn.PATH) @PathVariable("userId") String userId) {
        friendService.deleteFriend(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> friendSetUserIdPut(@Parameter(name = "userId", description = "", required = true, in = ParameterIn.PATH) @PathVariable("userId") String userId) {
        friendService.addFriend(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
