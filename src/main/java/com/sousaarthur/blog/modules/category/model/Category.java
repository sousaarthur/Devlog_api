package com.sousaarthur.blog.modules.category.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    /**<h2>Slug</h3>
     * Slug é um link gerado no backend focado em fornecer uma identificação idiomatica das categorias dos artigos.
     * <h3>Exemplos
     * <br>
     * <table>
     *     <tr>
     *         <th>Nome
     *         <th>Slug
     *     </tr>
     *     <tr>
     *         <td>Frontend</td>
     *         <td>frontend</td>
     *     </tr>
     *         <td>Desenvolvimento web</td>
     *         <td>desenvolvimento-web</td>
     *     </tr>
     * </table>
     * <h3>Regras</h3>
     * <ul>
     * <li>Limitar a 50 caracteres
     * <li>Bloquear caracteres especiais
     * </ul>
     * */
    private String slug;
    private boolean active;
}
