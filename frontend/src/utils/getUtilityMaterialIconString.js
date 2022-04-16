export const getUtilityMaterialIconString = (utility) => {
  const icons = new Map([
    ['Academia', 'fitness_center'],
    ['Churrasqueira', 'outdoor_grill'],
    ['Espaço Gourmet', 'countertops'],
    ['Espaço Verde/Parque', 'park'],
    ['Gramado', 'grass'],
    ['Jardim', 'yard'],
    ['Piscina', 'pool'],
    ['Playground', 'toys'],
    ['Quadra de Tênis', 'sports_tennis'],
    ['Quadra Poliesportiva', 'sports_score'],
    ['Quintal', 'fence'],
    ['Salão de Festa', 'celebration'],
    ['Salão de Jogos', 'sports_esports'],
    ['Wifi', 'wifi'],
    ['Lareira', 'fireplace'],
    ['Lavanderia', 'local_laundry_service'],
    ['Sala de Massagem', 'pinch'],
    ['Sauna', 'hot_tub'],
    ['Spa', 'spa'],
    ['Varanda', 'deck']
  ])

  return icons.get(utility) ?? 'error'
}