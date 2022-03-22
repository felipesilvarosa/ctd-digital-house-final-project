import { RMap, ROSM, RLayerVector, RStyle,RFeature } from "rlayers";
import { fromLonLat } from "ol/proj";
import "ol/ol.css";
import { Point } from "ol/geom";
import styles from "./ProductDetailsMap.module.scss"

export const ProductDetailsMap = (props) => {

  const center = fromLonLat([props.lon, props.lat]);

  return <>
    <section className={styles.MapSection}>
      <h2>Onde a acomodação está situada?</h2>
      <RMap className={styles.Map} initial={{ center: center, zoom: 11 }}>
        <ROSM />
        <RLayerVector zIndex={10}>
          <RStyle.RStyle>
            <RStyle.RIcon src="https://cdn-icons-png.flaticon.com/64/929/929426.png" anchor={[0.5, 0.8]} />
          </RStyle.RStyle>
          <RFeature
            geometry={new Point(center)}
            onClick={(e) =>
              e.map.getView().fit(e.target.getGeometry().getExtent(), {
                duration: 250,
                maxZoom: 15,
              })
            }
          >
          </RFeature>
        </RLayerVector>
      </RMap>
    </section>
  </>
}