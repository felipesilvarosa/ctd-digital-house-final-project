import { useSearchParams } from "react-router-dom"
import { HomeSearchBar, HomeCategories, HomeRecomendations, SpacingShim } from "src/components"

export const HomeView = () => {
  const [ query ] = useSearchParams()

  return (
    <>
      <SpacingShim height="4.25rem" />
      <HomeSearchBar />
      { !query.get("categoryId") && <HomeCategories /> }
      <HomeRecomendations categoryId={query.get("categoryId")} />
    </>
  )
}