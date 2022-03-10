import { useSearchParams } from "react-router-dom"
import { HomeSearchBar, HomeCategories, HomeRecomendations, SpacingShim } from "components"

export const HomeView = () => {
  const [ query ] = useSearchParams()

  return (
    <>
      <SpacingShim height="4.25rem" />
      <HomeSearchBar />
      { !query.get("category") && <HomeCategories /> }
      <HomeRecomendations category={query.get("category")} />
    </>
  )
}