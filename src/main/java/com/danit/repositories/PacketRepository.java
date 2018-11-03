package com.danit.repositories;

import com.danit.models.Packet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PacketRepository extends JpaRepository<Packet, UUID> {
}
