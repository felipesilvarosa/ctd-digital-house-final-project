import { Link } from "react-router-dom"
import { SpacingShim } from "src/components"
import styles from "./NotFoundView.module.scss"

export const NotFoundView = () => {
  return (
    <>
      <SpacingShim height="4.25rem" />
      <h1>Página não encontrada</h1>
      <p className={styles.Paragraph}>A página que você está tentando acessar não existe. <Link to="/" className={styles.Link}>Clique aqui para retornar para a página principal.</Link></p>
    </>
  )
}