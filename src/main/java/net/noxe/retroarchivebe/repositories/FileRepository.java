package net.noxe.retroarchivebe.repositories;

import net.noxe.retroarchivebe.entities.ArchiveFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<ArchiveFile, Long> {

}
